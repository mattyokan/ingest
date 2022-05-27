import {useEffect, useState} from "react";



export const DocumentContent = () => {
    const [content, setContent] = useState()

    useEffect(() => {
        const fetchContent = async () => {
            try {
                const res = await fetch("/api/feed")
                const json = await res.json()
                console.log("Result is ", json)
                setContent(json)
            } catch (err) {
                console.log(err)
            }
        }
        fetchContent()
    }, [])

    const mark = async (status) => {
        if(content && content.articleData) {
            await fetch(`/api/mark/${content.sourceId}?` + new URLSearchParams({
                id: content.articleData.id,
                flagged: status
            }), {
                method: 'POST',
            })
        }
    }

    const approveContent = async () => {
        await mark(true)
        (await fetch(`/api/content/${content.sourceId}?` + new URLSearchParams({
            id: content.articleData.id
        })))
            .then(res => res.json())
            .then(data => {
                // todo: report modal/endpoint
                console.log(data)
                window.location.reload()
            })
    }

    const declineContent = async () => {
        await mark(false)
        window.location.reload()

    }

    return (content ? (
        <section className="panel-content">
            <section className="decline">
                <button onClick={declineContent}>
                    Ignore
                </button>
            </section>
            <section className="content">
                <h1><b>Source:</b> {content?.sourceId}</h1>
                <h1><b>Title:</b> {content?.articleData?.title}</h1>
                <h2><b>Date:</b> {content?.articleData?.timestamp}</h2>
                <h2><b>URL:</b> <a href={content?.articleData?.source}>{content?.articleData?.source}</a></h2>

                {content.articleData.description}
            </section>
            <section className="approve">
                <button onClick={approveContent}>
                    Report
                </button>
            </section>
        </section>
    ) : (<section className="content">
        <h1>Loading...</h1>
    </section>))
}